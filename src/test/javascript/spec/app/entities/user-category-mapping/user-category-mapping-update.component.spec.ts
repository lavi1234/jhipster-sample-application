import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UserCategoryMappingUpdateComponent } from 'app/entities/user-category-mapping/user-category-mapping-update.component';
import { UserCategoryMappingService } from 'app/entities/user-category-mapping/user-category-mapping.service';
import { UserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

describe('Component Tests', () => {
  describe('UserCategoryMapping Management Update Component', () => {
    let comp: UserCategoryMappingUpdateComponent;
    let fixture: ComponentFixture<UserCategoryMappingUpdateComponent>;
    let service: UserCategoryMappingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [UserCategoryMappingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserCategoryMappingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserCategoryMappingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserCategoryMappingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserCategoryMapping(123);
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
        const entity = new UserCategoryMapping();
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
