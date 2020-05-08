import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UserSubscriptionDetailComponent } from 'app/entities/user-subscription/user-subscription-detail.component';
import { UserSubscription } from 'app/shared/model/user-subscription.model';

describe('Component Tests', () => {
  describe('UserSubscription Management Detail Component', () => {
    let comp: UserSubscriptionDetailComponent;
    let fixture: ComponentFixture<UserSubscriptionDetailComponent>;
    const route = ({ data: of({ userSubscription: new UserSubscription(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [UserSubscriptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserSubscriptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserSubscriptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userSubscription on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userSubscription).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
