import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { StaticPagesUpdateComponent } from 'app/entities/static-pages/static-pages-update.component';
import { StaticPagesService } from 'app/entities/static-pages/static-pages.service';
import { StaticPages } from 'app/shared/model/static-pages.model';

describe('Component Tests', () => {
  describe('StaticPages Management Update Component', () => {
    let comp: StaticPagesUpdateComponent;
    let fixture: ComponentFixture<StaticPagesUpdateComponent>;
    let service: StaticPagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [StaticPagesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StaticPagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StaticPagesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StaticPagesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StaticPages(123);
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
        const entity = new StaticPages();
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
