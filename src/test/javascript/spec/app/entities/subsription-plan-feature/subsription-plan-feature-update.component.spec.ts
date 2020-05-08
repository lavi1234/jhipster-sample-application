import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SubsriptionPlanFeatureUpdateComponent } from 'app/entities/subsription-plan-feature/subsription-plan-feature-update.component';
import { SubsriptionPlanFeatureService } from 'app/entities/subsription-plan-feature/subsription-plan-feature.service';
import { SubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

describe('Component Tests', () => {
  describe('SubsriptionPlanFeature Management Update Component', () => {
    let comp: SubsriptionPlanFeatureUpdateComponent;
    let fixture: ComponentFixture<SubsriptionPlanFeatureUpdateComponent>;
    let service: SubsriptionPlanFeatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SubsriptionPlanFeatureUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubsriptionPlanFeatureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubsriptionPlanFeatureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubsriptionPlanFeatureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubsriptionPlanFeature(123);
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
        const entity = new SubsriptionPlanFeature();
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
