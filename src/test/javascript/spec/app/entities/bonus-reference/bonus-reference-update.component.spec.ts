import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BonusReferenceUpdateComponent } from 'app/entities/bonus-reference/bonus-reference-update.component';
import { BonusReferenceService } from 'app/entities/bonus-reference/bonus-reference.service';
import { BonusReference } from 'app/shared/model/bonus-reference.model';

describe('Component Tests', () => {
  describe('BonusReference Management Update Component', () => {
    let comp: BonusReferenceUpdateComponent;
    let fixture: ComponentFixture<BonusReferenceUpdateComponent>;
    let service: BonusReferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BonusReferenceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BonusReferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BonusReferenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BonusReferenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BonusReference(123);
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
        const entity = new BonusReference();
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
