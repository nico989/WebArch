import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListParlamentsComponent } from './list-parlaments.component';

describe('ListParlamentsComponent', () => {
  let component: ListParlamentsComponent;
  let fixture: ComponentFixture<ListParlamentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListParlamentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListParlamentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
