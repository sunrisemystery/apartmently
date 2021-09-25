import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessengerWindowComponent } from './messenger-window.component';

describe('MessengerWindowComponent', () => {
  let component: MessengerWindowComponent;
  let fixture: ComponentFixture<MessengerWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessengerWindowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MessengerWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
