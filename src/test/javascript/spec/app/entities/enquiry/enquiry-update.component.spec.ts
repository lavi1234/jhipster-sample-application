import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryUpdateComponent } from 'app/entities/enquiry/enquiry-update.component';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { Enquiry } from 'app/shared/model/enquiry.model';

describe('Component Tests', () => {
  describe('Enquiry Management Update Component', () => {
    let comp: EnquiryUpdateComponent;
    let fixture: ComponentFixture<EnquiryUpdateComponent>;
    let service: EnquiryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EnquiryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnquiryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnquiryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Enquiry(123);
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
        const entity = new Enquiry();
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
