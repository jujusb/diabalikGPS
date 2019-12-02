import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiabalikComponent } from './diabalik.component';

describe('DiabalikComponent', () => {
  let component: DiabalikComponent;
  let fixture: ComponentFixture<DiabalikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiabalikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiabalikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
