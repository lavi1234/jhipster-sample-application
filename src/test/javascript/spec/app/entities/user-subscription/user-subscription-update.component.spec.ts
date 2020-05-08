import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UserSubscriptionUpdateComponent } from 'app/entities/user-subscription/user-subscription-update.component';
import { UserSubscriptionService } from 'app/entities/user-subscription/user-subscription.service';
import { UserSubscription } from 'app/shared/model/user-subscription.model';

describe('Component Tests', () => {
  describe('UserSubscription Management Update Component', () => {
    let comp: UserSubscriptionUpdateComponent;
    let fixture: ComponentFixture<UserSubscriptionUpdateComponent>;
    let service: UserSubscriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [UserSubscriptionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserSubscriptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserSubscriptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserSubscriptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserSubscription(123);
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
        const entity = new UserSubscription();
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
