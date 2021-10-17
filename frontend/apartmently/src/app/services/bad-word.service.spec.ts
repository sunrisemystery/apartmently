import { TestBed } from '@angular/core/testing';

import { BadWordService } from './bad-word.service';

describe('BadWordService', () => {
  let service: BadWordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BadWordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
