import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommissionReferenceDetailComponent } from 'app/entities/commission-reference/commission-reference-detail.component';
import { CommissionReference } from 'app/shared/model/commission-reference.model';

describe('Component Tests', () => {
  describe('CommissionReference Management Detail Component', () => {
    let comp: CommissionReferenceDetailComponent;
    let fixture: ComponentFixture<CommissionReferenceDetailComponent>;
    const route = ({ data: of({ commissionReference: new CommissionReference(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommissionReferenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommissionReferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommissionReferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commissionReference on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commissionReference).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
