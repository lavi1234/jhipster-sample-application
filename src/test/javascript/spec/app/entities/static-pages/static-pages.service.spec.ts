import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StaticPagesService } from 'app/entities/static-pages/static-pages.service';
import { IStaticPages, StaticPages } from 'app/shared/model/static-pages.model';

describe('Service Tests', () => {
  describe('StaticPages Service', () => {
    let injector: TestBed;
    let service: StaticPagesService;
    let httpMock: HttpTestingController;
    let elemDefault: IStaticPages;
    let expectedResult: IStaticPages | IStaticPages[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StaticPagesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StaticPages(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StaticPages', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.create(new StaticPages()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StaticPages', () => {
        const returnedFromService = Object.assign(
          {
            pageTitle: 'BBBBBB',
            heading: 'BBBBBB',
            description: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            publish: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StaticPages', () => {
        const returnedFromService = Object.assign(
          {
            pageTitle: 'BBBBBB',
            heading: 'BBBBBB',
            description: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            publish: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StaticPages', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
