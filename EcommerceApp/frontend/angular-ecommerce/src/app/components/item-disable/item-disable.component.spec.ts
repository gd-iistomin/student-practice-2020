import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemDisableComponent } from './item-disable.component';

describe('ItemDisableComponent', () => {
  let component: ItemDisableComponent;
  let fixture: ComponentFixture<ItemDisableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemDisableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemDisableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
