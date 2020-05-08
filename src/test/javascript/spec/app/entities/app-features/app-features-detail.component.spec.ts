import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AppFeaturesDetailComponent } from 'app/entities/app-features/app-features-detail.component';
import { AppFeatures } from 'app/shared/model/app-features.model';

describe('Component Tests', () => {
  describe('AppFeatures Management Detail Component', () => {
    let comp: AppFeaturesDetailComponent;
    let fixture: ComponentFixture<AppFeaturesDetailComponent>;
    const route = ({ data: of({ appFeatures: new AppFeatures(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [AppFeaturesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppFeaturesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppFeaturesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load appFeatures on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appFeatures).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
