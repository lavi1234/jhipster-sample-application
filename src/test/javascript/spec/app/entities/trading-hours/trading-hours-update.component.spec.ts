import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TradingHoursUpdateComponent } from 'app/entities/trading-hours/trading-hours-update.component';
import { TradingHoursService } from 'app/entities/trading-hours/trading-hours.service';
import { TradingHours } from 'app/shared/model/trading-hours.model';

describe('Component Tests', () => {
  describe('TradingHours Management Update Component', () => {
    let comp: TradingHoursUpdateComponent;
    let fixture: ComponentFixture<TradingHoursUpdateComponent>;
    let service: TradingHoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TradingHoursUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TradingHoursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TradingHoursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradingHoursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TradingHours(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TradingHours();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
