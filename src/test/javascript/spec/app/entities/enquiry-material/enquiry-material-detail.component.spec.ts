import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryMaterialDetailComponent } from 'app/entities/enquiry-material/enquiry-material-detail.component';
import { EnquiryMaterial } from 'app/shared/model/enquiry-material.model';

describe('Component Tests', () => {
  describe('EnquiryMaterial Management Detail Component', () => {
    let comp: EnquiryMaterialDetailComponent;
    let fixture: ComponentFixture<EnquiryMaterialDetailComponent>;
    const route = ({ data: of({ enquiryMaterial: new EnquiryMaterial(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryMaterialDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EnquiryMaterialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnquiryMaterialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load enquiryMaterial on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enquiryMaterial).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
