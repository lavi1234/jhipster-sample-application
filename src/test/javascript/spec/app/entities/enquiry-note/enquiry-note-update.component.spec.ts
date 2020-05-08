import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryNoteUpdateComponent } from 'app/entities/enquiry-note/enquiry-note-update.component';
import { EnquiryNoteService } from 'app/entities/enquiry-note/enquiry-note.service';
import { EnquiryNote } from 'app/shared/model/enquiry-note.model';

describe('Component Tests', () => {
  describe('EnquiryNote Management Update Component', () => {
    let comp: EnquiryNoteUpdateComponent;
    let fixture: ComponentFixture<EnquiryNoteUpdateComponent>;
    let service: EnquiryNoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryNoteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EnquiryNoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnquiryNoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnquiryNoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EnquiryNote(123);
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
        const entity = new EnquiryNote();
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
