import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LocalizationDetailComponent } from 'app/entities/localization/localization-detail.component';
import { Localization } from 'app/shared/model/localization.model';

describe('Component Tests', () => {
  describe('Localization Management Detail Component', () => {
    let comp: LocalizationDetailComponent;
    let fixture: ComponentFixture<LocalizationDetailComponent>;
    const route = ({ data: of({ localization: new Localization(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [LocalizationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocalizationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalizationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load localization on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.localization).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
