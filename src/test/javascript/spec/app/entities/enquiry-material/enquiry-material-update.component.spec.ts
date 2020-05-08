import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryMaterialUpdateComponent } from 'app/entities/enquiry-material/enquiry-material-update.component';
import { EnquiryMaterialService } from 'app/entities/enquiry-material/enquiry-material.service';
import { EnquiryMaterial } from 'app/shared/model/enquiry-material.model';

describe('Component Tests', () => {
  describe('EnquiryMaterial Management Update Component', () => {
    let comp: EnquiryMaterialUpdateComponent;
    let fixture: ComponentFixture<EnquiryMaterialUpdateComponent>;
    let service: EnquiryMaterialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryMaterialUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EnquiryMaterialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnquiryMaterialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnquiryMaterialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EnquiryMaterial(123);
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
        const entity = new EnquiryMaterial();
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
