import { TestBed } from '@angular/core/testing';

import { BoletimService } from './boletim.service';

describe('BoletimService', () => {
  let service: BoletimService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoletimService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
