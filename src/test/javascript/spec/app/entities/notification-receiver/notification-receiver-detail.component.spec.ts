import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { NotificationReceiverDetailComponent } from 'app/entities/notification-receiver/notification-receiver-detail.component';
import { NotificationReceiver } from 'app/shared/model/notification-receiver.model';

describe('Component Tests', () => {
  describe('NotificationReceiver Management Detail Component', () => {
    let comp: NotificationReceiverDetailComponent;
    let fixture: ComponentFixture<NotificationReceiverDetailComponent>;
    const route = ({ data: of({ notificationReceiver: new NotificationReceiver(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [NotificationReceiverDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NotificationReceiverDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificationReceiverDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notificationReceiver on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificationReceiver).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
