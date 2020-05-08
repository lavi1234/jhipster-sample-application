import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OfferPriceDetailComponent } from 'app/entities/offer-price/offer-price-detail.component';
import { OfferPrice } from 'app/shared/model/offer-price.model';

describe('Component Tests', () => {
  describe('OfferPrice Management Detail Component', () => {
    let comp: OfferPriceDetailComponent;
    let fixture: ComponentFixture<OfferPriceDetailComponent>;
    const route = ({ data: of({ offerPrice: new OfferPrice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OfferPriceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OfferPriceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferPriceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load offerPrice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerPrice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
