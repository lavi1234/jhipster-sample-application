import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UserCategoryMappingComponent } from 'app/entities/user-category-mapping/user-category-mapping.component';
import { UserCategoryMappingService } from 'app/entities/user-category-mapping/user-category-mapping.service';
import { UserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

describe('Component Tests', () => {
  describe('UserCategoryMapping Management Component', () => {
    let comp: UserCategoryMappingComponent;
    let fixture: ComponentFixture<UserCategoryMappingComponent>;
    let service: UserCategoryMappingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [UserCategoryMappingComponent],
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
        .overrideTemplate(UserCategoryMappingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserCategoryMappingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserCategoryMappingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserCategoryMapping(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userCategoryMappings && comp.userCategoryMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserCategoryMapping(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userCategoryMappings && comp.userCategoryMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
