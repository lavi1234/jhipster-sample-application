import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OfferPriceUpdateComponent } from 'app/entities/offer-price/offer-price-update.component';
import { OfferPriceService } from 'app/entities/offer-price/offer-price.service';
import { OfferPrice } from 'app/shared/model/offer-price.model';

describe('Component Tests', () => {
  describe('OfferPrice Management Update Component', () => {
    let comp: OfferPriceUpdateComponent;
    let fixture: ComponentFixture<OfferPriceUpdateComponent>;
    let service: OfferPriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OfferPriceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OfferPriceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferPriceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferPriceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OfferPrice(123);
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
        const entity = new OfferPrice();
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
