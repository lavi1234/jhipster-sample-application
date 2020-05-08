import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryMaterialComponent } from 'app/entities/enquiry-material/enquiry-material.component';
import { EnquiryMaterialService } from 'app/entities/enquiry-material/enquiry-material.service';
import { EnquiryMaterial } from 'app/shared/model/enquiry-material.model';

describe('Component Tests', () => {
  describe('EnquiryMaterial Management Component', () => {
    let comp: EnquiryMaterialComponent;
    let fixture: ComponentFixture<EnquiryMaterialComponent>;
    let service: EnquiryMaterialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryMaterialComponent],
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
        .overrideTemplate(EnquiryMaterialComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnquiryMaterialComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnquiryMaterialService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EnquiryMaterial(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.enquiryMaterials && comp.enquiryMaterials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EnquiryMaterial(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.enquiryMaterials && comp.enquiryMaterials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
