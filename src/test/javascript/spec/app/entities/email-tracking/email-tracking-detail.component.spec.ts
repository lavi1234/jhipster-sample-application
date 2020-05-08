import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EmailTrackingDetailComponent } from 'app/entities/email-tracking/email-tracking-detail.component';
import { EmailTracking } from 'app/shared/model/email-tracking.model';

describe('Component Tests', () => {
  describe('EmailTracking Management Detail Component', () => {
    let comp: EmailTrackingDetailComponent;
    let fixture: ComponentFixture<EmailTrackingDetailComponent>;
    const route = ({ data: of({ emailTracking: new EmailTracking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EmailTrackingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmailTrackingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailTrackingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emailTracking on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emailTracking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
