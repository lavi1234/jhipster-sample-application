import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { StaticPagesDetailComponent } from 'app/entities/static-pages/static-pages-detail.component';
import { StaticPages } from 'app/shared/model/static-pages.model';

describe('Component Tests', () => {
  describe('StaticPages Management Detail Component', () => {
    let comp: StaticPagesDetailComponent;
    let fixture: ComponentFixture<StaticPagesDetailComponent>;
    const route = ({ data: of({ staticPages: new StaticPages(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [StaticPagesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StaticPagesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StaticPagesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load staticPages on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.staticPages).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
