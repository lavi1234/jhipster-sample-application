import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SubscriptionPlanDetailComponent } from 'app/entities/subscription-plan/subscription-plan-detail.component';
import { SubscriptionPlan } from 'app/shared/model/subscription-plan.model';

describe('Component Tests', () => {
  describe('SubscriptionPlan Management Detail Component', () => {
    let comp: SubscriptionPlanDetailComponent;
    let fixture: ComponentFixture<SubscriptionPlanDetailComponent>;
    const route = ({ data: of({ subscriptionPlan: new SubscriptionPlan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SubscriptionPlanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubscriptionPlanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubscriptionPlanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subscriptionPlan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subscriptionPlan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
