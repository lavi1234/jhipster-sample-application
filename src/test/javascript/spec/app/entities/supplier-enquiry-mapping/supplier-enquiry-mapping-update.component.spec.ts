import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SupplierEnquiryMappingUpdateComponent } from 'app/entities/supplier-enquiry-mapping/supplier-enquiry-mapping-update.component';
import { SupplierEnquiryMappingService } from 'app/entities/supplier-enquiry-mapping/supplier-enquiry-mapping.service';
import { SupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';

describe('Component Tests', () => {
  describe('SupplierEnquiryMapping Management Update Component', () => {
    let comp: SupplierEnquiryMappingUpdateComponent;
    let fixture: ComponentFixture<SupplierEnquiryMappingUpdateComponent>;
    let service: SupplierEnquiryMappingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SupplierEnquiryMappingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupplierEnquiryMappingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierEnquiryMappingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierEnquiryMappingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupplierEnquiryMapping(123);
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
        const entity = new SupplierEnquiryMapping();
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
