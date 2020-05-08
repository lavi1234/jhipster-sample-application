import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LocalizationUpdateComponent } from 'app/entities/localization/localization-update.component';
import { LocalizationService } from 'app/entities/localization/localization.service';
import { Localization } from 'app/shared/model/localization.model';

describe('Component Tests', () => {
  describe('Localization Management Update Component', () => {
    let comp: LocalizationUpdateComponent;
    let fixture: ComponentFixture<LocalizationUpdateComponent>;
    let service: LocalizationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [LocalizationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocalizationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalizationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalizationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Localization(123);
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
        const entity = new Localization();
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
