import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MessageDeleteDetailsUpdateComponent } from 'app/entities/message-delete-details/message-delete-details-update.component';
import { MessageDeleteDetailsService } from 'app/entities/message-delete-details/message-delete-details.service';
import { MessageDeleteDetails } from 'app/shared/model/message-delete-details.model';

describe('Component Tests', () => {
  describe('MessageDeleteDetails Management Update Component', () => {
    let comp: MessageDeleteDetailsUpdateComponent;
    let fixture: ComponentFixture<MessageDeleteDetailsUpdateComponent>;
    let service: MessageDeleteDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [MessageDeleteDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MessageDeleteDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MessageDeleteDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MessageDeleteDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MessageDeleteDetails(123);
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
        const entity = new MessageDeleteDetails();
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
