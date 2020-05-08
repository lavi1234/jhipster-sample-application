import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UserCategoryMappingDetailComponent } from 'app/entities/user-category-mapping/user-category-mapping-detail.component';
import { UserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

describe('Component Tests', () => {
  describe('UserCategoryMapping Management Detail Component', () => {
    let comp: UserCategoryMappingDetailComponent;
    let fixture: ComponentFixture<UserCategoryMappingDetailComponent>;
    const route = ({ data: of({ userCategoryMapping: new UserCategoryMapping(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [UserCategoryMappingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserCategoryMappingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserCategoryMappingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userCategoryMapping on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userCategoryMapping).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
