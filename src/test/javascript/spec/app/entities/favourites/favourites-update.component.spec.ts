import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FavouritesUpdateComponent } from 'app/entities/favourites/favourites-update.component';
import { FavouritesService } from 'app/entities/favourites/favourites.service';
import { Favourites } from 'app/shared/model/favourites.model';

describe('Component Tests', () => {
  describe('Favourites Management Update Component', () => {
    let comp: FavouritesUpdateComponent;
    let fixture: ComponentFixture<FavouritesUpdateComponent>;
    let service: FavouritesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FavouritesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FavouritesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FavouritesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FavouritesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Favourites(123);
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
        const entity = new Favourites();
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
