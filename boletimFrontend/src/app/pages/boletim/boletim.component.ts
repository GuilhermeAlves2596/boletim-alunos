import { Component, OnInit, OnDestroy } from '@angular/core';
import { BoletimService } from '../../services/boletim.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Avaliacao, BoletimResponse, Disciplina, GradeDto, SaveGradesRequest, Turma } from '../../model/boletim.models';
import { Subscription } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-boletim',
  templateUrl: './boletim.component.html',
  styleUrls: ['./boletim.component.scss']
})
export class BoletimComponent implements OnInit, OnDestroy {
  turmas: Turma[] = [];
  disciplinas: Disciplina[] = [];
  selectedTurmaNome: string = '';
  selectedDisciplinaNome: string = '';

  turmaId: number | null = null;
  disciplinaId: number | null = null;
  avaliacoes: Avaliacao[] = [];
  form = this.fb.group({
    rows: this.fb.array([])
  });

  displayedColumns: string[] = [];
  private subs: Subscription[] = [];
  loading = false;
  saveInProgress = false;

  constructor(private svc: BoletimService, private fb: FormBuilder, private snack: MatSnackBar) { }

  ngOnInit(): void {
    this.loadTurmas();
  }

  ngOnDestroy(): void {
    this.subs.forEach(s => s.unsubscribe());
  }

  get rows(): FormArray {
    return this.form.get('rows') as FormArray;
  }

  private loadTurmas() {
    this.svc.getTurmas().subscribe({
      next: (t) => {
        this.turmas = t;
        if (t.length === 1) {
          this.onTurmaChange(t[0].id);
        }
      },
      error: (err) => console.error(err)
    });
  }

  onTurmaChange(turmaId: number | null) {
    this.turmaId = turmaId;
    this.disciplinaId = null;
    this.disciplinas = [];
    this.selectedDisciplinaNome = '';

    this.selectedTurmaNome = this.turmas.find(t => t.id === turmaId)?.nome ?? '';

    if (this.turmaId == null) {
      this.rows.clear();
      return;
    }

    this.svc.getDisciplinas(this.turmaId).subscribe({
      next: ds => {
        this.disciplinas = ds;
        if (ds.length === 1) {
          this.onDisciplinaChange(ds[0].id);
        }
      },
      error: err => console.error(err)
    });
  }

  onDisciplinaChange(disciplinaId: number | null) {
    this.disciplinaId = disciplinaId;
    this.selectedDisciplinaNome = this.disciplinas.find(d => d.id === disciplinaId)?.nome ?? '';

    if (this.turmaId != null && this.disciplinaId != null) {
      this.loadBoletim();
    }
  }

  public loadBoletim() {
    if (this.turmaId == null || this.disciplinaId == null) {
      console.warn('turmaId ou disciplinaId nulos — abortando loadBoletim');
      return;
    }

    this.loading = true;
    this.svc.getBoletim(this.turmaId!, this.disciplinaId!).subscribe({
      next: (resp) => this.buildForm(resp),
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  private buildForm(resp: BoletimResponse) {
    this.avaliacoes = resp.avaliacoes;
    this.rows.clear();

    // montar colunas
    this.displayedColumns = ['alunoNome', ...this.avaliacoes.map(a => `av-${a.id}`), 'media'];

    resp.alunos.forEach(aluno => {
      const notasGroup = this.fb.group({});
      this.avaliacoes.forEach(av => {
        const controlName = `av-${av.id}`;
        const value = aluno.notas ? aluno.notas[av.id] ?? null : null;
        notasGroup.addControl(controlName, this.fb.control(value, [
          Validators.min(0),
          Validators.max(10)
        ]));
      });

      const rowGroup = this.fb.group({
        alunoId: [aluno.alunoId],
        alunoNome: [aluno.alunoNome],
        notas: notasGroup,
        media: [aluno.media]
      });

      const sub = notasGroup.valueChanges.subscribe(() => {
        const media = this.calcularMediaParaForm(notasGroup);
        rowGroup.patchValue({ media }, { emitEvent: false });
      });
      this.subs.push(sub);

      this.rows.push(rowGroup);
    });

    this.loading = false;
  }


  private calcularMediaParaForm(notasGroup: FormGroup): number | null {
    let somaPonderada = 0;
    let somaPesos = 0;
    for (const av of this.avaliacoes) {
      const controlName = `av-${av.id}`;
      const value = notasGroup.get(controlName)?.value;
      if (value === null || value === undefined || value === '') continue;
      const n = Number(value);
      if (Number.isNaN(n)) continue;
      somaPonderada += n * av.peso;
      somaPesos += av.peso;
    }
    if (somaPesos === 0) return null;
    const media = somaPonderada / somaPesos;
    return Math.round(media * 10) / 10;
  }

  formatMedia(media: number | null): string {
    return media === null ? '—' : media.toFixed(1);
  }

  isAnyInvalid(): boolean {
    for (let i = 0; i < this.rows.length; i++) {
      const notas = (this.rows.at(i) as FormGroup).get('notas') as FormGroup;
      if (notas.invalid) return true;
    }
    return false;
  }

  onSave() {
    if (this.isAnyInvalid()) {
      this.rows.controls.forEach(r => (r as FormGroup).get('notas')!.markAllAsTouched());
      return;
    }

    if (this.turmaId == null || this.disciplinaId == null) {
      this.snack.open('Selecione turma e disciplina antes de salvar.', 'Fechar', {
        duration: 5000,
        horizontalPosition: 'right',
        verticalPosition: 'top'
      });
      return;
    }

    const grades: GradeDto[] = [];

    for (let i = 0; i < this.rows.length; i++) {
      const row = this.rows.at(i) as FormGroup;
      const alunoId: number = row.get('alunoId')!.value;
      const notas = row.get('notas') as FormGroup;

      for (const av of this.avaliacoes) {
        const ctrl = notas.get(`av-${av.id}`);
        const val = ctrl?.value;

        if (val !== null && val !== '' && val !== undefined) {
          const num = Number(val);
          if (!Number.isNaN(num)) {
            const constrained = Math.min(Math.max(num, 0), 10);
            grades.push({
              alunoId,
              avaliacaoId: av.id,
              valor: constrained
            });
          }
        }
      }
    }

    const payload: SaveGradesRequest = {
      turmaId: this.turmaId!,
      disciplinaId: this.disciplinaId!,
      grades
    };

    this.saveInProgress = true;
    this.svc.saveGrades(payload).subscribe({
      next: () => {
        this.saveInProgress = false;
        this.loadBoletim();
        this.snack.open('Notas salvas com sucesso!', 'Fechar', {
          duration: 5000,
          horizontalPosition: 'right',
          verticalPosition: 'top'
        });
      },
      error: (err) => {
        this.saveInProgress = false;
        this.snack.open('Erro ao salvar notas. Tente novamente.', 'Fechar', {
          duration: 5000,
          horizontalPosition: 'right',
          verticalPosition: 'top'
        });
      }
    });
  }
}
