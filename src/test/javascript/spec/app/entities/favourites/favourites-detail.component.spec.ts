import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FavouritesDetailComponent } from 'app/entities/favourites/favourites-detail.component';
import { Favourites } from 'app/shared/model/favourites.model';

describe('Component Tests', () => {
  describe('Favourites Management Detail Component', () => {
    let comp: FavouritesDetailComponent;
    let fixture: ComponentFixture<FavouritesDetailComponent>;
    const route = ({ data: of({ favourites: new Favourites(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FavouritesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FavouritesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FavouritesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load favourites on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.favourites).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
