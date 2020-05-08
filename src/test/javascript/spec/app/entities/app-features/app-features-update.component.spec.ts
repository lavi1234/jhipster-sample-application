import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AppFeaturesUpdateComponent } from 'app/entities/app-features/app-features-update.component';
import { AppFeaturesService } from 'app/entities/app-features/app-features.service';
import { AppFeatures } from 'app/shared/model/app-features.model';

describe('Component Tests', () => {
  describe('AppFeatures Management Update Component', () => {
    let comp: AppFeaturesUpdateComponent;
    let fixture: ComponentFixture<AppFeaturesUpdateComponent>;
    let service: AppFeaturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [AppFeaturesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppFeaturesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppFeaturesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppFeaturesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AppFeatures(123);
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
        const entity = new AppFeatures();
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
