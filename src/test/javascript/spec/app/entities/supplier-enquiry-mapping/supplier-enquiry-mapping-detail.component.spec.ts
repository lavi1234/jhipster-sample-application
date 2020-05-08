import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SupplierEnquiryMappingDetailComponent } from 'app/entities/supplier-enquiry-mapping/supplier-enquiry-mapping-detail.component';
import { SupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';

describe('Component Tests', () => {
  describe('SupplierEnquiryMapping Management Detail Component', () => {
    let comp: SupplierEnquiryMappingDetailComponent;
    let fixture: ComponentFixture<SupplierEnquiryMappingDetailComponent>;
    const route = ({ data: of({ supplierEnquiryMapping: new SupplierEnquiryMapping(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SupplierEnquiryMappingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupplierEnquiryMappingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierEnquiryMappingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load supplierEnquiryMapping on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierEnquiryMapping).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
