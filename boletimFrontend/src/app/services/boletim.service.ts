import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BoletimResponse, Disciplina, SaveGradesRequest, Turma } from '../model/boletim.models';

@Injectable({
  providedIn: 'root'
})
export class BoletimService {

  private base = 'http://localhost:8080/api/boletim';

  constructor(private http: HttpClient) {}

  getBoletim(turmaId: number, disciplinaId: number): Observable<BoletimResponse> {
    return this.http.get<BoletimResponse>(`${this.base}?turmaId=${turmaId}&disciplinaId=${disciplinaId}`);
  }

  saveGrades(payload: SaveGradesRequest): Observable<void> {
    return this.http.post<void>(`${this.base}/save`, payload);
  }

  getTurmas(): Observable<Turma[]> {
  return this.http.get<Turma[]>(`${this.base}/turmas`);
}

  getDisciplinas(turmaId?: number): Observable<Disciplina[]> {
    const url = turmaId ? `${this.base}/disciplinas?turmaId=${turmaId}` : `${this.base}/disciplinas`;
    return this.http.get<Disciplina[]>(url);
  }
}
