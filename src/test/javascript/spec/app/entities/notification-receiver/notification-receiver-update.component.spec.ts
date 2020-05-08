import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { NotificationReceiverUpdateComponent } from 'app/entities/notification-receiver/notification-receiver-update.component';
import { NotificationReceiverService } from 'app/entities/notification-receiver/notification-receiver.service';
import { NotificationReceiver } from 'app/shared/model/notification-receiver.model';

describe('Component Tests', () => {
  describe('NotificationReceiver Management Update Component', () => {
    let comp: NotificationReceiverUpdateComponent;
    let fixture: ComponentFixture<NotificationReceiverUpdateComponent>;
    let service: NotificationReceiverService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [NotificationReceiverUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NotificationReceiverUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificationReceiverUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificationReceiverService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NotificationReceiver(123);
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
        const entity = new NotificationReceiver();
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
