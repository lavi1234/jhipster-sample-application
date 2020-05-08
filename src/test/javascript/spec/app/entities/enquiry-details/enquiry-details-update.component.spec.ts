import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryDetailsUpdateComponent } from 'app/entities/enquiry-details/enquiry-details-update.component';
import { EnquiryDetailsService } from 'app/entities/enquiry-details/enquiry-details.service';
import { EnquiryDetails } from 'app/shared/model/enquiry-details.model';

describe('Component Tests', () => {
  describe('EnquiryDetails Management Update Component', () => {
    let comp: EnquiryDetailsUpdateComponent;
    let fixture: ComponentFixture<EnquiryDetailsUpdateComponent>;
    let service: EnquiryDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EnquiryDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnquiryDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnquiryDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EnquiryDetails(123);
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
        const entity = new EnquiryDetails();
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
