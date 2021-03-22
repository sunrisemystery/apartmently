import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAdComponent } from './add-ad.component';

describe('AddAdComponent', () => {
  let component: AddAdComponent;
  let fixture: ComponentFixture<AddAdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddAdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
