import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemEnableComponent } from './item-enable.component';

describe('ItemEnableComponent', () => {
  let component: ItemEnableComponent;
  let fixture: ComponentFixture<ItemEnableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemEnableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemEnableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
