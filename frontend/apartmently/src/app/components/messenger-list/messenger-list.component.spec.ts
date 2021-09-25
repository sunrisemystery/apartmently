import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessengerListComponent } from './messenger-list.component';

describe('MessengerListComponent', () => {
  let component: MessengerListComponent;
  let fixture: ComponentFixture<MessengerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessengerListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MessengerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
