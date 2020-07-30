import { TestBed } from '@angular/core/testing';

import { HyperSeleniumService } from './hyper-selenium.service';

describe('HyperSeleniumService', () => {
  let service: HyperSeleniumService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HyperSeleniumService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
