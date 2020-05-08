import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EmailTrackingUpdateComponent } from 'app/entities/email-tracking/email-tracking-update.component';
import { EmailTrackingService } from 'app/entities/email-tracking/email-tracking.service';
import { EmailTracking } from 'app/shared/model/email-tracking.model';

describe('Component Tests', () => {
  describe('EmailTracking Management Update Component', () => {
    let comp: EmailTrackingUpdateComponent;
    let fixture: ComponentFixture<EmailTrackingUpdateComponent>;
    let service: EmailTrackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EmailTrackingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmailTrackingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailTrackingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailTrackingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmailTracking(123);
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
        const entity = new EmailTracking();
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
