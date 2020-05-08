import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TradingHoursDetailComponent } from 'app/entities/trading-hours/trading-hours-detail.component';
import { TradingHours } from 'app/shared/model/trading-hours.model';

describe('Component Tests', () => {
  describe('TradingHours Management Detail Component', () => {
    let comp: TradingHoursDetailComponent;
    let fixture: ComponentFixture<TradingHoursDetailComponent>;
    const route = ({ data: of({ tradingHours: new TradingHours(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TradingHoursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TradingHoursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TradingHoursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tradingHours on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tradingHours).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
