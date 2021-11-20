import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardParlamentsComponent } from './card-parlaments.component';

describe('CardParlamentsComponent', () => {
  let component: CardParlamentsComponent;
  let fixture: ComponentFixture<CardParlamentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CardParlamentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CardParlamentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
