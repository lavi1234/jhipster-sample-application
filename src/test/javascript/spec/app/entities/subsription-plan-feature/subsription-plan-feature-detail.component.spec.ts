import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SubsriptionPlanFeatureDetailComponent } from 'app/entities/subsription-plan-feature/subsription-plan-feature-detail.component';
import { SubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

describe('Component Tests', () => {
  describe('SubsriptionPlanFeature Management Detail Component', () => {
    let comp: SubsriptionPlanFeatureDetailComponent;
    let fixture: ComponentFixture<SubsriptionPlanFeatureDetailComponent>;
    const route = ({ data: of({ subsriptionPlanFeature: new SubsriptionPlanFeature(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SubsriptionPlanFeatureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubsriptionPlanFeatureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubsriptionPlanFeatureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subsriptionPlanFeature on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subsriptionPlanFeature).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
