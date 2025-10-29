import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoletimComponent } from './boletim.component';

describe('BoletimComponent', () => {
  let component: BoletimComponent;
  let fixture: ComponentFixture<BoletimComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BoletimComponent]
    });
    fixture = TestBed.createComponent(BoletimComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
