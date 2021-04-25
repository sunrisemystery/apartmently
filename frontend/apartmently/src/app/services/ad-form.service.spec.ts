import { TestBed } from '@angular/core/testing';

import { AdFormService } from './ad-form.service';

describe('AdFormService', () => {
  let service: AdFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
