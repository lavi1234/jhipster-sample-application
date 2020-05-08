import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SupplierEnquiryMappingComponent } from 'app/entities/supplier-enquiry-mapping/supplier-enquiry-mapping.component';
import { SupplierEnquiryMappingService } from 'app/entities/supplier-enquiry-mapping/supplier-enquiry-mapping.service';
import { SupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';

describe('Component Tests', () => {
  describe('SupplierEnquiryMapping Management Component', () => {
    let comp: SupplierEnquiryMappingComponent;
    let fixture: ComponentFixture<SupplierEnquiryMappingComponent>;
    let service: SupplierEnquiryMappingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SupplierEnquiryMappingComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(SupplierEnquiryMappingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierEnquiryMappingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierEnquiryMappingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SupplierEnquiryMapping(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.supplierEnquiryMappings && comp.supplierEnquiryMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SupplierEnquiryMapping(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.supplierEnquiryMappings && comp.supplierEnquiryMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
