import { TestBed } from '@angular/core/testing';

import { HyperSeleniumServerService } from './hyper-selenium-server.service';

describe('HyperSeleniumServerService', () => {
  let service: HyperSeleniumServerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HyperSeleniumServerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
