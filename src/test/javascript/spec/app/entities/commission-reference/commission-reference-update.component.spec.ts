import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommissionReferenceUpdateComponent } from 'app/entities/commission-reference/commission-reference-update.component';
import { CommissionReferenceService } from 'app/entities/commission-reference/commission-reference.service';
import { CommissionReference } from 'app/shared/model/commission-reference.model';

describe('Component Tests', () => {
  describe('CommissionReference Management Update Component', () => {
    let comp: CommissionReferenceUpdateComponent;
    let fixture: ComponentFixture<CommissionReferenceUpdateComponent>;
    let service: CommissionReferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommissionReferenceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommissionReferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommissionReferenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommissionReferenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommissionReference(123);
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
        const entity = new CommissionReference();
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
