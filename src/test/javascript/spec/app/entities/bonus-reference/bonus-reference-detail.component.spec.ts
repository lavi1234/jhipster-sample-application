import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BonusReferenceDetailComponent } from 'app/entities/bonus-reference/bonus-reference-detail.component';
import { BonusReference } from 'app/shared/model/bonus-reference.model';

describe('Component Tests', () => {
  describe('BonusReference Management Detail Component', () => {
    let comp: BonusReferenceDetailComponent;
    let fixture: ComponentFixture<BonusReferenceDetailComponent>;
    const route = ({ data: of({ bonusReference: new BonusReference(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BonusReferenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BonusReferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BonusReferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bonusReference on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bonusReference).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
