import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SubscriptionPlanUpdateComponent } from 'app/entities/subscription-plan/subscription-plan-update.component';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/subscription-plan.service';
import { SubscriptionPlan } from 'app/shared/model/subscription-plan.model';

describe('Component Tests', () => {
  describe('SubscriptionPlan Management Update Component', () => {
    let comp: SubscriptionPlanUpdateComponent;
    let fixture: ComponentFixture<SubscriptionPlanUpdateComponent>;
    let service: SubscriptionPlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SubscriptionPlanUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubscriptionPlanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubscriptionPlanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubscriptionPlanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubscriptionPlan(123);
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
        const entity = new SubscriptionPlan();
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
