import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SubsriptionPlanFeatureComponent } from 'app/entities/subsription-plan-feature/subsription-plan-feature.component';
import { SubsriptionPlanFeatureService } from 'app/entities/subsription-plan-feature/subsription-plan-feature.service';
import { SubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

describe('Component Tests', () => {
  describe('SubsriptionPlanFeature Management Component', () => {
    let comp: SubsriptionPlanFeatureComponent;
    let fixture: ComponentFixture<SubsriptionPlanFeatureComponent>;
    let service: SubsriptionPlanFeatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SubsriptionPlanFeatureComponent],
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
        .overrideTemplate(SubsriptionPlanFeatureComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubsriptionPlanFeatureComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubsriptionPlanFeatureService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubsriptionPlanFeature(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subsriptionPlanFeatures && comp.subsriptionPlanFeatures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubsriptionPlanFeature(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subsriptionPlanFeatures && comp.subsriptionPlanFeatures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
